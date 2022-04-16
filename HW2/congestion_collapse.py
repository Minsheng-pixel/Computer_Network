#!/usr/bin/env python3
from simulator import Simulator
from sliding_window_host import SlidingWindowHost


def return_congested_simulator(host):
    # TODO: Create simulator that shows a congestion collapse setting.
    simulator = Simulator(host = host, seed = 1000, queue_limit=1000000, loss_ratio=0, rtt_min= 10)
    # Use  0.0 for loss_ratio, 1000 for the seed, and 1000000 for queue_limit.
    return simulator


def tick_and_get_seq_number(window):
    host = SlidingWindowHost(window, verbose=False)
    simulator = return_congested_simulator(host)
    for tick in range(0, 10000):
        simulator.tick(tick)
    # Return the largest sequence number that has been received in order
    print(
        "Maximum in order received sequence number "
        + str(simulator.host.in_order_rx_seq)
    )
    return simulator.host.in_order_rx_seq


def get_window_sizes():
    # TODO: Select a progression of window sizes, which show a congestion collapse curve.
    window_sizes = []
    for i in range (10):
        window_sizes.append(i* 100)
    return window_sizes


def main():
    window_sizes = get_window_sizes()
    # Should have at least 10 entries.
    assert len(window_sizes) >= 10
    # Windows should be strictly increasing
    assert all(x <= y for x, y in zip(window_sizes, window_sizes[1:]))
    # TODO: For each window size, call tick_and_get_seq_number
    seq_numbers = []
    for i in window_sizes:
        seq_numbers.append (tick_and_get_seq_number(i))
    # TODO: Collect the results
    # Optional" Plot the results


if __name__ == "__main__":
    main()
