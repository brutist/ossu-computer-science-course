"""Functions to prevent a nuclear meltdown."""


def is_criticality_balanced(temperature, neutrons_emitted):
    """Verify criticality is balanced.

    :param temperature: int or float - temperature value in kelvin.
    :param neutrons_emitted: int or float - number of neutrons emitted per second.
    :return: bool - is criticality balanced?

    A reactor is said to be critical if it satisfies the following conditions:
    - The temperature is less than 800 K.
    - The number of neutrons emitted per second is greater than 500.
    - The product of temperature and neutrons emitted per second is less than 500000.
    """
    MAX_TEMP = 800
    MIN_NEUTRON = 500
    MAX_PRODUCT = 500000

    return temperature < MAX_TEMP and neutrons_emitted > MIN_NEUTRON and (temperature*neutrons_emitted) < MAX_PRODUCT


def reactor_efficiency(voltage, current, theoretical_max_power):
    """Assess reactor efficiency zone.

    :param voltage: int or float - voltage value.
    :param current: int or float - current value.
    :param theoretical_max_power: int or float - power that corresponds to a 100% efficiency.
    :return: str - one of ('green', 'orange', 'red', or 'black').

    Efficiency can be grouped into 4 bands:

    1. green -> efficiency of 80% or more,
    2. orange -> efficiency of less than 80% but at least 60%,
    3. red -> efficiency below 60%, but still 30% or more,
    4. black ->  less than 30% efficient.

    The percentage value is calculated as
    (generated power/ theoretical max power)*100
    where generated power = voltage * current
    """
    generated_power = voltage * current
    efficiency = (generated_power / theoretical_max_power) * 100

    GREEN = 80
    ORANGE = 60
    RED = 30

    if efficiency >= GREEN:
        return 'green'
    elif efficiency >= ORANGE:
        return 'orange'
    elif efficiency >= RED:
        return 'red'
    else:
        return 'black'
    


def fail_safe(temperature, neutrons_produced_per_second, threshold):
    """Assess and return status code for the reactor.

    :param temperature: int or float - value of the temperature in kelvin.
    :param neutrons_produced_per_second: int or float - neutron flux.
    :param threshold: int or float - threshold for category.
    :return: str - one of ('LOW', 'NORMAL', 'DANGER').

    1. 'LOW' -> `temperature * neutrons per second` < 90% of `threshold`
    2. 'NORMAL' -> `temperature * neutrons per second` +/- 10% of `threshold`
    3. 'DANGER' -> `temperature * neutrons per second` is not in the above-stated ranges
    """

    status = temperature * neutrons_produced_per_second
    NORMAL_RANGE = threshold * 0.1
    LOW = threshold * 0.9

    if status < LOW:
        return 'LOW'
    elif status <= (threshold + NORMAL_RANGE) and status >= (threshold - NORMAL_RANGE):
        return 'NORMAL'
    else:
        return 'DANGER'
