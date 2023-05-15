# 6.0001/6.00 Problem Set 5 - RSS Feed Filter
# Name: Jonathan Mauring Jr. 
# Collaborators: None
# Time: May 9 - 

import feedparser
import string
import time
import threading
from project_util import translate_html
from mtTkinter import *
from datetime import datetime
import pytz
from zoneinfo import ZoneInfo

#-----------------------------------------------------------------------

#======================
# Code for retrieving and parsing
# Google and Yahoo News feeds
# Do not change this code
#======================

def process(url):
    """
    Fetches news items from the rss url and parses them.
    Returns a list of NewsStory-s.
    """
    feed = feedparser.parse(url)
    entries = feed.entries
    ret = []
    for entry in entries:
        guid = entry.guid
        title = translate_html(entry.title)
        link = entry.link
        description = translate_html(entry.description)
        pubdate = translate_html(entry.published)

        try:
            pubdate = datetime.strptime(pubdate, "%a, %d %b %Y %H:%M:%S %Z")
            pubdate.replace(tzinfo=pytz.timezone("GMT"))
          #  pubdate = pubdate.astimezone(pytz.timezone('EST'))
          #  pubdate.replace(tzinfo=None)
        except ValueError:
            pubdate = datetime.strptime(pubdate, "%a, %d %b %Y %H:%M:%S %z")

        newsStory = NewsStory(guid, title, description, link, pubdate)
        ret.append(newsStory)
    return ret

#======================
# Data structure design
#======================

# Problem 1
class NewsStory(object):
    def __init__(self, guid, title, description, link, pubdate):
        self.guid = guid
        self.title = title
        self.description = description
        self.link = link
        self.pubdate = pubdate

    def get_guid(self):
        return self.guid

    def get_title(self):
        return self.title
    
    def get_description(self):
        return self.description
    
    def get_link(self):
        return self.link
    
    def get_pubdate(self):
        return self.pubdate

#======================
# Triggers
#======================

class Trigger(object):
    def evaluate(self, story):
        """
        Returns True if an alert should be generated
        for the given news item, or False otherwise.
        """
        # DO NOT CHANGE THIS!
        raise NotImplementedError

# PHRASE TRIGGERS

# Problem 2
# TODO: PhraseTrigger ->>> DONE

class PhraseTrigger(Trigger):
    '''
    This is a parent Class of TitleTrigger and DescriptionTrigger

    phrase (string) - could be any string
    '''
    def __init__(self, phrase):
        # phrase should be case insensitive
        self.phrase = phrase.lower()

    def is_phrase_in(self, text):
        # text should be in the same case as the phrase
        word_separator = string.punctuation + ' '
        text = text.lower()

        # replace all word_separator with ' ' (space)
        for j in text:
            if j in word_separator:
                text = text.replace(j, ' ')

        # split and join to remove ' ' (space) in text
        text = ' '.join(text.split())

        # detects whether the text is in title
        if self.phrase in text:
            length_text = len(text)
            phrase_end = text.index(self.phrase) + len(self.phrase)

            # if the end index of the phrase is also the end index of the text - no conflict
            # we are trying to avoid cases in which the text is a plural form of the phrase
            if phrase_end == length_text:
                return True

            # if the next char in text is not another letter - no conflict
            # I'm trying to avoid this confition
            # phrase = 'sup' text = 'sups' - is_phrase_in(text) should return False
            elif text[phrase_end] in word_separator:
                return True
        
        return False


# Problem 3
# TODO: TitleTrigger ->>> DONE

class TitleTrigger(PhraseTrigger):
    
    # PhraseTrigger has is_phrase_in() which takes in a text and check whether the self.phrase is in text
    def __init__(self, phrase):
        PhraseTrigger.__init__(self, phrase)
    
    def evaluate(self, story):
        title = story.get_title()

        return self.is_phrase_in(title)


# Problem 4
# TODO: DescriptionTrigger ->>> DONE

class DescriptionTrigger(PhraseTrigger):

    # PhraseTrigger has is_phrase_in() which takes in a text and check whether the self.phrase is in text
    def __init__(self, phrase):
        PhraseTrigger.__init__(self, phrase)

    def evaluate(self, story):
        description = story.get_description()

        return self.is_phrase_in(description)


# TIME TRIGGERS

# Problem 5
# TODO: TimeTrigger ->>> DONE
# Constructor:
#        Input: Time has to be in EST and in the format of "%d %b %Y %H:%M:%S".
#        Convert time from string to a datetime before saving it as an attribute.
class TimeTrigger(Trigger):
    
    def __init__(self, time):
       
        # input EST time only
        time = datetime.strptime(time, '%d %b %Y %H:%M:%S')

        # convert time to a 'aware' format to compare it to pubdate
        eastern = pytz.timezone('US/Eastern')
        time = eastern.localize(time)
        
        # this part is supposed to convert standard GMT to EST but realized it was not needed
        # time = time.replace(tzinfo = zoneinfo.ZoneInfo('US/Eastern'))

        self.time = time
    

# Problem 6
# TODO: BeforeTrigger and AfterTrigger ->>> DONE
class BeforeTrigger(TimeTrigger):

    def __init__(self, time):
        TimeTrigger.__init__(self, time)

    def evaluate(self, story):
        pub_date = story.get_pubdate().replace(tzinfo = ZoneInfo('US/Eastern'))
        
        if self.time > pub_date:
            return True
        
        return False

        
class AfterTrigger(TimeTrigger):

    def __init__(self, time):
        TimeTrigger.__init__(self, time)

    def evaluate(self, story):
        pub_date = story.get_pubdate().replace(tzinfo = ZoneInfo('US/Eastern'))
        
        if self.time < pub_date:
            return True
        
        return False

        
# COMPOSITE TRIGGERS

# Problem 7
# TODO: NotTrigger ->>> DONE
class NotTrigger(Trigger):
    '''
    Takes in a trigger and evaluates to the opposite of the given trigger.
    '''
    def __init__(self, trigger):
        self.trigger = trigger
    
    def evaluate(self, story):
        return not self.trigger.evaluate(story)

# Problem 8
# TODO: AndTrigger ->>> DONE
class AndTrigger(Trigger):
    '''
    Takes in two triggers and evalutes True if both Triggers are True; False if otherwise.
    '''
    def __init__(self, trigger1, trigger2):
        self.trigger1 = trigger1
        self.trigger2 = trigger2

    def evaluate(self, story):
        return self.trigger1.evaluate(story) and self.trigger2.evaluate(story)

# Problem 9
# TODO: OrTrigger ->>> DONE
class OrTrigger(Trigger):
    '''
    Takes in two triggers and evaluates True if one or two of the Triggers are True.
    '''
    def __init__(self, trigger1, trigger2):
        self.trigger1 = trigger1
        self.trigger2 = trigger2

    def evaluate(self, story):
        return self.trigger1.evaluate(story) or self.trigger2.evaluate(story)

#======================
# Filtering
#======================

# Problem 10
def filter_stories(stories, triggerlist):
    """
    Takes in a list of NewsStory instances.

    Returns: a list of only the stories for which a trigger in triggerlist fires.
    """
    # TODO: Problem 10

    filtered_stories = []
    for story in stories:
        for trigger in triggerlist:
            if trigger.evaluate(story): 
                filtered_stories.append(story)
    
    return filtered_stories



#======================
# User-Specified Triggers
#======================
# Problem 11
def read_trigger_config(filename):
    """
    filename: the name of a trigger configuration file

    Returns: a list of trigger objects specified by the trigger configuration
        file.
    """
    # We give you the code to read in the file and eliminate blank lines and
    # comments. You don't need to know how it works for now!
    trigger_file = open(filename, 'r')
    lines = []
    for line in trigger_file:
        line = line.rstrip()
        if not (len(line) == 0 or line.startswith('//')):
            lines.append(line)

    # TODO: Problem 11
    # line is the list of lines that you need to parse and for which you need
    # to build triggers
    KEYWORDS_1 = ['TITLE', 'DESCRIPTION', 'AFTER', 'BEFORE']
    KEYWORDS_2 = ['AND', 'OR']

    TRIGGER_TYPE_1 = [TitleTrigger, DescriptionTrigger, AfterTrigger, BeforeTrigger, NotTrigger]
    TRIGGER_TYPE_2 = [AndTrigger, OrTrigger]
    all_triggers = {}
    triggers = []
    names = []
    # map the keywords in the file lines to its corresponding functions
    for line in lines:
        line = line.strip().split(',')
        
        # create a dictionary with name of trigger as key and trigger object as values
        if line[1] in KEYWORDS_1:
            all_triggers[line[0]] = TRIGGER_TYPE_1[KEYWORDS_1.index(line[1])](line[2])
            
        if line[1] == 'NOT':
            all_triggers[line[0]] = NotTrigger(all_triggers[line[2]])

        if line[1] in KEYWORDS_2:
            all_triggers[line[0]] = TRIGGER_TYPE_2[KEYWORDS_2.index(line[1])](all_triggers[line[2]], all_triggers[line[3]])

        # create the list of triggers
        if line[0] == 'ADD':
            for i in range(1, len(line)):
                names.append(line[i])

    # append the trigger to the trigger_list; returns an error if the trigger name is not found
    for name in names:
        triggers.append(all_triggers.get(name))

    return triggers

SLEEPTIME = 120 #seconds -- how often we poll

def main_thread(master):
    # A sample trigger list - you might need to change the phrases to correspond
    # to what is currently in the news
    try:
        t1 = TitleTrigger("Philippines")
        t2 = DescriptionTrigger("dead")
        t3 = DescriptionTrigger("new")
        t4 = AndTrigger(t2, t3)
        triggerlist = [t1, t4]

        # Problem 11
        # TODO: After implementing read_trigger_config, uncomment this line 
        triggerlist = read_trigger_config('new_triggers.txt')
       
        # HELPER CODE - you don't need to understand this!
        # Draws the popup window that displays the filtered stories
        # Retrieves and filters the stories from the RSS feeds
        frame = Frame(master)
        frame.pack(side=BOTTOM)
        scrollbar = Scrollbar(master)
        scrollbar.pack(side=RIGHT,fill=Y)

        t = "Google & Yahoo Top News"
        title = StringVar()
        title.set(t)
        ttl = Label(master, textvariable=title, font=("Helvetica", 18))
        ttl.pack(side=TOP)
        cont = Text(master, font=("Helvetica",14), yscrollcommand=scrollbar.set)
        cont.pack(side=BOTTOM)
        cont.tag_config("title", justify='center')
        button = Button(frame, text="Exit", command=root.destroy)
        button.pack(side=BOTTOM)
        guidShown = []
        def get_cont(newstory):
            if newstory.get_guid() not in guidShown:
                cont.insert(END, newstory.get_title()+"\n", "title")
                cont.insert(END, "\n---------------------------------------------------------------\n", "title")
                cont.insert(END, newstory.get_description())
                cont.insert(END, "\n*********************************************************************\n", "title")
                guidShown.append(newstory.get_guid())

        while True:

            print("Polling . . .", end=' ')
            # Get stories from Google's Top Stories RSS news feed
            stories = process("http://news.google.com/news?output=rss")
    
            # Get stories from Yahoo's Top Stories RSS news feed
            #stories.extend(process("http://news.yahoo.com/rss/topstories"))

            stories = filter_stories(stories, triggerlist)

            list(map(get_cont, stories))
            scrollbar.config(command=cont.yview)


            print("Sleeping...")
            time.sleep(SLEEPTIME)

    except Exception as e:
        print(e)


if __name__ == '__main__':
    root = Tk()
    root.title("Some RSS parser")
    t = threading.Thread(target=main_thread, args=(root,))
    t.start()
    root.mainloop()

