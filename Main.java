public class Main {
	public static volatile Object lock = new Object();
	public static volatile int produtos = 0;
	
	public static void main(String[] args) {
		Produtor p1 = new Produtor(1);
		p1.start();
		Consumidor c1 = new Consumidor(1);
		c1.start();
	}
}

class Produtor extends Thread {
	int id = 0;
	Produtor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 1000; i ++ ) {			
			synchronized( Main.lock )
			{	
				if(Main.produtos < 100) {
					Main.produtos = Main.produtos + 1;
					/*
					 * 
					 * */
					Main.lock.notify();
				}
				System.out.println("#<- Produtor " + id + ";\t\t estoque = " + Main.produtos);
			}
		}
	}
}
class Consumidor extends Thread {
	int id = 0;
	Consumidor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 1000; i++) {	
			
			synchronized( Main.lock )
			{	
				if (Main.produtos <= 0) {
					System.out.println("#~~ Consumidor " + id +  ";\t triste :'(");
					/*
					 * 
					 * */
					try {
						Main.lock.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(Main.produtos > 0)
					Main.produtos = Main.produtos - 1;
				System.out.println("#-> Consumidor " + id + ";\t estoque = " + Main.produtos);
			}
		
		}
	}
}