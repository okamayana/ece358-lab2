package lab2;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
	
	private Queue<Packet> mPacketQueue;
	private long mNextArrivalTick = -1;
	private boolean mIsTransmitting = false;
	
	public Node() {
		mPacketQueue = new LinkedList<Packet>();
	}
	
	public void enqueuePacket(Packet packet) {
		mPacketQueue.add(packet);
	}
	
	public Packet dequeuePacket() {
		return mPacketQueue.remove();
	}
	
	public Packet peekQueue() {
		return mPacketQueue.peek();
	}

	public void decrementArrivalTick() {
		if (mNextArrivalTick > 0) {
			mNextArrivalTick--;
		}
	}
	
	public boolean hasPacketArrived() {
		return mNextArrivalTick == 0 && mNextArrivalTick != -1;
	}
	
	public void startTransmit() {
		mIsTransmitting = true;
	}
	
	public void stopTransmit() {
		mIsTransmitting = false;
	}
	
	public boolean isTransmitting() {
		return mIsTransmitting;
	}
	
	public long getNextArrivalTick() {
		return mNextArrivalTick;
	}
	
	public void setNextArrivalTick(long nextArrivalTick) {
		mNextArrivalTick = nextArrivalTick;
	}
	
}
