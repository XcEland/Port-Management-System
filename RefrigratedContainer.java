public class RefrigratedContainer extends HeavyConytainer{
    public RefrigratedContainer(int ID,int weight){
        super(ID,weight);
    }
    public double consumption(){
        return 4.00*getWeight();
    }
}
