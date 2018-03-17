package Recrui;

import java.util.ArrayList;

import java.util.List;

public class ThreadPool {
	List<Runnable> tasks = new ArrayList<>();
	int size = 3;
	Object lock=new Object();

	public ThreadPool() {
		synchronized (lock) {
			for (int i = 0; i < size; i++) {
				new worker("线程" + i).start();
				System.out.println("线程" + i + "启动");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class worker extends Thread {
		public worker(String name) {
			super(name);
		}

		Runnable task;

		public void run() {

			while (true) {
				synchronized (lock) {
					while (tasks.size() == 0) {
						try {
							System.out.println(this.getName() + "进入等待");
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					task = tasks.remove(0);
					System.out.println(this.getName() + "离开等待，开始执行任务");
					
				}
				task.run();
			}
		}
	}

	public void add(Runnable r) {
		synchronized (lock) {
			tasks.add(r);
			System.out.println("添加任务" + r.toString());
			lock.notifyAll();
		}

	}
}
