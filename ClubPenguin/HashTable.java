public class HashTable<E>{
	private DLList<E>[] table;
	@SuppressWarnings("unchecked")
	public HashTable(){
		table = new DLList[10];
		for(int i = 0; i<table.length; i++){
			table[i] = new DLList<E>();
		}
	}
	public void add(E obj){
		Item temp = (Item)obj;
		int hash = temp.getCount() % 10; 
		/*
		if(table[position] == null)
			table[position] = new DLList<E>();
		*/
		table[hash].add(obj);
	}
	public DLList<E>[] getTable(){
		return table;
	}
	public String toString(){
		String msg = "";
		for(int i = 0; i<table.length; i++){
			if(table[i].size()!=0) //
				msg += "Bucket:" + i + " - " + table[i].toString() + "\n";
		}
		return msg;
	}
	public void remove(E obj){
		Item temp = (Item)obj;
		int hash = temp.getCount() % 10; 
		table[hash] = new DLList<E>();
	}
}