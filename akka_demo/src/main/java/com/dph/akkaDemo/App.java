package com.dph.akkaDemo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.BoundedMessageQueueSemantics;
import akka.dispatch.RequiresMessageQueue;
import akka.japi.Creator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("actor-demo-java");
		ActorRef hello = system.actorOf(Hello.props());
		int count = 0;
		while (true) {
			hello.tell("Bob", ActorRef.noSender());
			System.out.println(++ count);
		}
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// /* ignore */ }
		// system.shutdown();
	}

	private static class Hello extends UntypedActor implements RequiresMessageQueue<BoundedMessageQueueSemantics> {

		private static class HelloCreator implements Creator<Hello> {
			private static final long serialVersionUID = 1L;

			@Override
			public Hello create() throws Exception {
				return new Hello();
			}

		}

		public static Props props() {
			return Props.create(new HelloCreator());
		}

		public void onReceive(Object message) throws Exception {
			if (message instanceof String) {
				System.out.println("Hello " + message);
				Thread.sleep(1000);
			}
		}
	}
}
