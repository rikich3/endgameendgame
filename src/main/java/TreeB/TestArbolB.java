package TreeB;

public class TestArbolB {
    public static void main(String[] args) {
        int t = 5;
        ArbolB<Integer> arbolB = new ArbolB<Integer>(t);
        int[] valores = {20, 10, 50, 30, 60,70,80,90,100,5,25,23,120,130,140,150,160,1,2,3,40,45,35,65,21,51,52};


        for(int i=0; i<valores.length; i++) {
            System.out.println("\nInsertando... valor " + valores[i]);
            arbolB.insertar(valores[i]);
            arbolB.mostrarArbolB();
    
            
        }
        
    }
    
}
