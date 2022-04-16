from packet import Packet
from timeout_calculator import TimeoutCalculator

# Structure to store information associated with an unacked packet
# so that we can maintain a list of such UnackedPacket objects


class AimdHost:
    """
    This class implements a host that follows the AIMD protocol.
    Data members of this class are

    **unacked**: List of unacked packets

    **window**: Size of the window at any given moment

    **max_seq**: Maximum sequence number sent so far

    **in_order_rx_seq**: Maximum sequence number received so far

    **slow_start**: Boolean to indicate whether algorithm is in slow start or not

    **next_decrease**: Time (in ticks) at which the window size should be decreased

    **timeout_calculator**: An object of class TimeoutCalculator
    (Refer to TimeoutCalculator class for more information)

    There are two member functions - send and recv that perform the task of sending
    and receiving packets respectively. All send and receive logic should be written
    within one of these two functions.

    """

    def __init__(
        self,
        verbose=True,
        min_timeout=TimeoutCalculator.MIN_TIMEOUT,
        max_timeout=TimeoutCalculator.MAX_TIMEOUT,
    ):
        # list of unacked packets
        self.unacked = []
        self.window = 1
        # We'll initialize window to 1
        self.max_seq = -1
        # maximum sequence number sent so far
        self.in_order_rx_seq = -1
        # maximum sequence number received so far in order
        # Are we in slow start?
        self.slow_start = True
        # When to next decrease your window; adds some hystersis
        self.next_decrease = -1
        # Whether to print output
        self.verbose = verbose
        # object for computing timeouts
        self.timeout_calculator = TimeoutCalculator(
            verbose=verbose, min_timeout=min_timeout, max_timeout=max_timeout
        )

    def send(self, tick):
        """
        Function to send packet on to the network. Host should first retransmit
         any unacked packets that have timed out.
         Host should also decrease the window size if it is time for the next
         decrease. After attempting retransmissions, if the window is not full,
         fill up the window with new packets.

        Args:

            **tick**: Simulated time

        Returns:

            A list of packets that the host wants to transmit on to the network
        """
        if self.verbose:
            print("@ tick " + str(tick) + " window is " + str(self.window))

        # TODO: Create an empty list of packets that the host will send
        pkt_send = []
        # First, process retransmissions
        for i in range(0, len(self.unacked)):
            unacked_pkt = self.unacked[i]
            if tick >= unacked_pkt.timeout_tick:
                if self.verbose:
                    print(
                        "@ "
                        + str(tick)
                        + " timeout for unacked_pkt "
                        + str(unacked_pkt.seq_num)
                        + " timeout duration was "
                        + str(unacked_pkt.timeout_duration)
                    )
                # TODO: Retransmit any packet that has timed out
                pkt = Packet(tick, unacked_pkt.seq_num)
                pkt.num_retx = unacked_pkt.num_retx + 1
                pkt_send.append(pkt)
                timeout = self.timeout_calculator.exp_backoff()
                pkt.timeout_duration = timeout  # what is the duration of its timeout
                pkt.timeout_tick = tick + timeout
                self.unacked[i]= pkt
                # by doing the following in order
                # (1) creating a new packet,
                # (2) Incrementing num_retx (for debugging purposes)
                # (3) Append the packet to the list of packets created earlier
                # (4) Backing off the timer
                # (5) Updating timeout_tick and timeout_duration appropriately after backing off the timer
                if self.verbose:
                    print(
                        "@ "
                        + str(tick)
                        + " exp backoff for packet "
                        + str(unacked_pkt.seq_num)
                    )
                # TODO: Multiplicative decrease, if it's time for the next decrease
                if self.window // 2 > 1 and tick >= self.next_decrease:
                    self.window = self.window//2
                    self.next_decrease = self.next_decrease + self.timeout_calculator.mean_rtt
                # Cut window by half, but don't let it go below 1
                # TODO: Make sure the next multiplicative decrease doesn't happen until an RTT later
                # (use the timeout_calculator to estimate the RTT)

                # Exit slow start, whether you were in it or not
                self.slow_start = False


        # Now fill up the window with new packets
        while len(self.unacked) < self.window:
            # TODO: Replace this break
            # TODO: Create new packets, set their retransmission timeout, and transmit them
            # TODO: Remember to update self.max_seq and add the just sent packet to self.unacked
            for i in range(self.window-len(self.unacked)):
                pkt = Packet(tick, self.max_seq)
                pkt.timeout_tick = pkt.sent_ts + self.timeout_calculator.timeout
                pkt.timeout_duration = self.timeout_calculator.timeout
                pkt_send.append(pkt)
                self.max_seq = self.max_seq + 1
                self.unacked.append(pkt)
        # TODO: Return the list of packets that need to be sent on to the network
        return pkt_send

    def recv(self, pkt, tick):
        """
        Function to get a packet from the network.

        Args:

            **pkt**: Packet received from the network

            **tick**: Simulated time
        """
        assert tick > pkt.sent_ts
        pass
        # TODO: Compute RTT sample
        RTT_sample = tick - pkt.sent_ts
        # TODO: Update timeout
        self.timeout_calculator.update_timeout(RTT_sample)
        # TODO: Remove received packet from self.unacked
        for pkt_ in self.unacked:
            if pkt_.seq_num == pkt.seq_num:
                self.unacked.remove(pkt_)
        # TODO: Update in_order_rx_seq to reflect the largest sequence number that you
        # have received in order so far
        if len(self.unacked) != 0:
            self.in_order_rx_seq = self.unacked[0].seq_num
        else:
            self.in_order_rx_seq = self.max_seq
        # TODO: Increase your window given that you just received an ACK. Remember that:
        self.window= self.window+1

            # 1. The window increase rule is different for slow start and congestion avoidance.
        # 2. The recv() function is called on every ACK (not every RTT), so you should adjust your window accordingly.
