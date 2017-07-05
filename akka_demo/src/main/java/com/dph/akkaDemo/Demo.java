package com.dph.akkaDemo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

class Demo extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	{
		for (Object msg : new Object[] { "lowpriority", "lowpriority", "highpriority", "pigdog", "pigdog2", "pigdog3", "highpriority", PoisonPill.getInstance() }) {
			getSelf().tell(msg, getSelf());
		}
	}

	public void onReceive(Object message) {
		log.info(message.toString());
	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("actor-demo-java");
		// We create a new Actor that just prints out what it processes
		ActorRef myActor = system.actorOf(Props.create(Demo.class).withDispatcher("prio-dispatcher"));
		/*
		 * Logs: ’highpriority ’highpriority ’pigdog ’pigdog2 ’pigdog3 ’lowpriority ’lowpriority
		 */
	}
}