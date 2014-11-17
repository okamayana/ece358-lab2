package lab2;

import java.util.ArrayList;
import java.util.Random;

public class Simulator {
	
	private static final long TICKS = 100000000; // 100,000,000 ticks per simulation;
	private static final long TICK_VALUE = 1000000; // 1,000,000 ticks per second => 1 tick = 1 us
	
	private static final int TIME_TP = 512; // back-off Tp 512 bit-time
	private static final int TIME_CARRIER_SENSE = 96; // sense 96 bit-time
	private static final int TIME_JAMMING = 48; // jamming signal 48 bit-time
	
	private static final int DISTANCE_NODES = 10; // 10 m between nodes
	private static final int PROP_SPEED = 2 * (10^8); // 2E+8 m/s
	private static final int BITS_PER_TICK = (int) (1000000 * 8 / TICK_VALUE);
	
	private int N = 0; // 		N	number of nodes (nodes)
	private int A = 0; // 		A	average packet arrival rate (packets/second)
	private int W = 0; // 		W	speed of medium (Mbps)
	private int L = 0; // 		L	packet length (bytes)
	private double P = 0; //	P	persistence parameter P (N/A) 
	
	private boolean mIsMediumFree = true;
	
	private ArrayList<Node> mNodes;
	
	public Simulator(int N, int A, int W, int L) {
		this.N = N;
		this.A = A;
		this.W = W;
		this.L = L;
		init();
	}
	
	public void simulate() {
		
		int packetsSuccessCount = 0;
		int packetsTotalCount = 0;
		int packetsDelayTotal = 0;
		
		for (long tick = 0; tick < TICKS; tick++) {
			
			for (Node node : mNodes) {
				if (node.getNextArrivalTick() == -1) {
					long nextArrivalTick = getNextArrivalTick();
					node.setNextArrivalTick(nextArrivalTick);
				} else if (!node.hasPacketArrived()) {
					node.decrementArrivalTick();
				} else if (node.hasPacketArrived()) {					
					node.setNextArrivalTick(-1);
					
					Packet packet = new Packet();
					if (node.isTransmitting()) {
						node.enqueuePacket(packet);
					} else {
						node.startTransmit();
					}
				}
			}
		}
	}
	
	private long getNextArrivalTick() {
		return convertTimeToTicks(randVar(A));
	}
	
	private long getBackOffTick(int i) {
		return TIME_TP * calculateR((2^i) - 1);
	}
	
	public int calculateR(int max) {
	    Random rand = new Random();
	    return rand.nextInt(max + 1);
	}
	
	private double randVar(double lambda) {
		return (-1 / lambda) * Math.log(1.0 - Math.random());
	}
	
	private long convertTimeToTicks(double seconds) {
		return (long) (seconds * TICK_VALUE);
	}
	
	private void init() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < N; i++) {
			Node node = new Node();
			nodes.add(node);
		}
		
		mNodes = nodes;
	}
	
	public void reset() {
		
	}
}
