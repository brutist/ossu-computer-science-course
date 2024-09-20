#include <algorithm>
#include <iostream>
#include <list>
#include <string>
#include <vector>

using std::cin;
using std::list;
using std::string;
using std::vector;
using bucket = list<string>;

struct Query {
    string type, s;
    size_t ind;
};

class QueryProcessor {
    int bucket_count;
    // store all strings in one vector
    vector<bucket> buckets;
    size_t hash_func(const string &s) const {
        static const size_t multiplier = 263;
        static const size_t prime = 1000000007;
        unsigned long long hash = 0;
        for (int i = static_cast<int>(s.size()) - 1; i >= 0; --i)
            hash = (hash * multiplier + s[i]) % prime;
        return hash % bucket_count;
    }

  private:
    bool bucketContains(bucket &B, string &query) const {
        return std::find(B.begin(), B.end(), query) != B.end();
    }

    void writeBucket(bucket b) {
        for (string s : b) {
            std::cout << s << " ";
        }
        std::cout << "\n";
    }

  public:
    explicit QueryProcessor(int bucket_count)
        : bucket_count(bucket_count), buckets(bucket_count, list<string>(0)) {}

    Query readQuery() const {
        Query query;
        cin >> query.type;
        if (query.type != "check")
            cin >> query.s;
        else
            cin >> query.ind;
        return query;
    }

    void writeSearchResult(bool was_found) const {
        std::cout << (was_found ? "yes\n" : "no\n");
    }

    void processQuery(const Query &query) {
        if (query.type == "check") {
            writeBucket(buckets[query.ind]);
        }

        else {
            int index = static_cast<int>(hash_func(query.s));
            string q = query.s;

            if (query.type == "find")
                writeSearchResult(bucketContains(buckets[index], q));

            else if (query.type == "add") {
                if (!bucketContains(buckets[index], q)) {
                    buckets[index].push_front(q);
                }
            }

            else if (query.type == "del") {
                if (bucketContains(buckets[index], q)) {
                    buckets[index].remove(q);
                }
            }
        }
    }

    void processQueries() {
        int n;
        cin >> n;
        for (int i = 0; i < n; ++i)
            processQuery(readQuery());
    }
};

int main() {
    std::ios_base::sync_with_stdio(false);
    int bucket_count;
    cin >> bucket_count;
    QueryProcessor proc(bucket_count);
    proc.processQueries();
    return 0;
}
