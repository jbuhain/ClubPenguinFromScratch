import java.io.Serializable;

public class DLList<E> implements Serializable{
  private Node<E> dummy;
  private int size;
  
  
  public DLList(){
   	dummy = new Node<E>(null);
    dummy.setNext(dummy);
    dummy.setPrev(dummy);
    size = 0;
    
  }
  
  private Node<E> getNode(int loc){
    Node<E> current = dummy;
    if(loc < size / 2){
      for(int i = -1; i < loc; i++){
       current = current.next(); 
      }
    }else{
      for(int i = size; i > loc; i--){
        current = current.prev();
      }
  	}
    return current;
  }
  
  public void add(E data){
   	Node<E> temp = new Node<E>(data);
    if(size == 0){
      dummy.setNext(temp);
      dummy.setPrev(temp);
      temp.setNext(dummy);
      temp.setPrev(dummy);
    }else{
      Node<E> prev = dummy.prev();
      prev.setNext(temp);
      dummy.setPrev(temp);
      temp.setNext(dummy);
      temp.setPrev(prev);
    }
    size++;
  }
  
  public void add(int loc, E data){
   	Node<E> current = getNode(loc);
    Node<E> prev = current.prev();
    Node<E> temp = new Node<E>(data);
    prev.setNext(temp);
    current.setPrev(temp);
    temp.setNext(current);
    temp.setPrev(prev);
    size++;
  }
  
  public E get(int loc){
   return getNode(loc).get(); 
  }
  public void remove(int loc){
   	Node<E> toRemove = getNode(loc);
    Node<E> prev = toRemove.prev();
    Node<E> next = toRemove.next();
    prev.setNext(next);
    next.setPrev(prev);
    size--;
  }
  public void remove(E data){
   	Node<E> current = dummy.next();
    int loc = 0;
    while(!current.get().equals(data)){
      current = current.next();
      loc++;
	  if(current.equals(dummy)){
		  return;
	  }
    }
    remove(loc);
  }
  public boolean contains(E data){
   	Node<E> current = dummy.next();
    while(!current.equals(dummy)){
      if(current.get().equals(data)){
        return true;
      }
	  current = current.next();
    }
	return false;
  }
  public void set(int loc, E data){
    Node<E> current = getNode(loc);
    current.set(data);
  }
  
  public String toString(){
   	String temp = "[";
    Node<E> current = dummy.next();
    while(!current.equals(dummy)){
      temp += current.get().toString();
      current = current.next();
      if(!current.equals(dummy)){
       temp += ", "; 
      }
    }
    temp += "]";
    return temp;
  }
  
  public int size(){
   return size; 
  }
  public void print(){
	  print(dummy.next());
  }
  public void print(Node<E> temp){
	  if(temp.get()!=null){
		  System.out.println(temp.get().toString());
		  print(temp.next());
	  }
  }
  public void reverse(){
	  int index = 0;
	  for(int i = 0; i<size/2; i++){
		  index = size - i - 1;
		  E temp = getNode(i).get(); 
		  getNode(i).set(get(index));
		  getNode(index).set(temp); 
	  }
  }
  public void reverseAlternate(){
	  Node<E> current = dummy; 
	  for(int i = -1; i<size; i++){ //make i equal to -1 cuz of dummy
		  Node<E> temp = current.next(); 
		  current.setNext(current.prev());
		  current.setPrev(temp);
		  current = current.next();
	  }
  }
}