"""Functions which helps the locomotive engineer to keep track of the train."""


def get_list_of_wagons(*args):
    """Return a list of wagons.

    :param: arbitrary number of wagons.
    :return: list - list of wagons.
    """
    return args


def fix_list_of_wagons(each_wagons_id, missing_wagons):
    """Fix the list of wagons.

    :parm each_wagons_id: list - the list of wagons.
    :parm missing_wagons: list - the list of missing wagons.
    :return: list - list of wagons.
    """
    index = each_wagon_id.index(1)
    modified_wagon_lists = each_wagon_id[2:] + each_wagon_id[3:index] + missing_wagons + each_wagons_id[index:] + each_wagon_id[:3]

    return modified_wagon_lists

def add_missing_stops(a, **kwargs):
    """Add missing stops to route dict.

    :param route: dict - the dict of routing information.
    :param: arbitrary number of stops.
    :return: dict - updated route dictionary.
    """
    updated_route = {}
    cities =[]
    for k, v in kwargs.items():
        updated_route[a].append(v)

    return updated_route


def extend_route_information(route, more_route_information):
    """Extend route information with more_route_information.

    :param route: dict - the route information.
    :param more_route_information: dict -  extra route information.
    :return: dict - extended route information.
    """
    return route + more_route_information


def fix_wagon_depot(wagons_rows):
    """Fix the list of rows of wagons.

    :param wagons_rows: list[list[tuple]] - the list of rows of wagons.
    :return: list[list[tuple]] - list of rows of wagons.
    """
    # create an empty list of list
    modified_wagon_rows = []
    for rows in wagon_rows:
        modified_wagon_rows.append([])

    for i in range(len(wagon_rows)):

        for j in range(len(wagon_rows[i])):
            modified_wagon_rows.append(wagon_rows[i][j])

    return modified_wagon_rows
