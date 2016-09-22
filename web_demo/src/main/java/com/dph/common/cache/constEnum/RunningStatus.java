package com.dph.common.cache.constEnum;

public enum RunningStatus {
	STARTING(0), STARTED(1), RUNNING(2), WAITING(3), STOPPING(4), STOPPED(5);

	private final int value;

	private RunningStatus(int value) {
		this.value = value;
	}

	public RunningStatus valueOf(int value) {
		switch (value) {
		case 0:
			return STARTING;
		case 1:
			return STARTED;
		case 2:
			return RUNNING;
		case 3:
			return WAITING;
		case 4:
			return STOPPING;
		case 5:
			return STOPPED;
		default:
			return null;
		}
	}

	public int value() {
		return value;
	}

}
