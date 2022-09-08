

public class IR {

    class Operation {
        public String opcode;
        public String SR1;
        public String SR2;
        public String SR3;
        public int line;

        public Operation(){
            this.opcode = "";
            this.SR1 = "";
            this.SR2 = "";
            this.SR3 = "";
            this.line = 0;
        }
        
        public void setOp(String op){
            this.opcode = op;
        }
        
        public void setSR1(String sr){
            this.SR1 = sr;
        }

        public void setSR2(String sr){
            this.SR2 = sr;
        }

        public void setSR3(String sr){
            this.SR3 = sr;
        }

        public void setLine(int line){
            this.line = line;
        }


    }

    class Node{  
        Operation op;  
        Node previous;  
        Node next;  
   
        public Node(Operation op) {  
            this.op = op;  
        }  
    }  
    
    Node head, tail = null;  
   
    public void addNode(Operation op) {  

        Node node = new Node(op);  

        if(head == null) {  
            head = tail = node;  
            head.previous = null;  
            tail.next = null;  
        }  
        else {  
            tail.next = node;   
            node.previous = tail;  
            tail = node;  
            tail.next = null;  
        }  
    }

    public void printNodes() {  

        Node current = head;  
        if(head == null) {  
            System.out.println("");  
            System.out.println("Due to the syntax error, run terminates.");  
            return;  
        }  
        
        while(current != null) {  
            System.out.println(current.op.opcode + " [ " + current.op.SR1 + " ], " + " [ " + current.op.SR2 + " ], " + " [ " + current.op.SR3 + " ]");  
            current = current.next;  
        }  
    } 


}