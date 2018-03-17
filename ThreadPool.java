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
				new worker("�߳�" + i).start();
				System.out.println("�߳�" + i + "����");
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
							System.out.println(this.getName() + "����ȴ�");
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					task = tasks.remove(0);
					System.out.println(this.getName() + "�뿪�ȴ�����ʼִ������");
					
				}
				task.run();
			}
		}
	}

	public void add(Runnable r) {
		synchronized (lock) {
			tasks.add(r);
			System.out.println("�������" + r.toString());
			lock.notifyAll();
		}

	}
}
