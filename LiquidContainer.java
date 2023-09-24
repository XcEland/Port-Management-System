public class LiquidContainer extends HeavyConytainer{
    public LiquidContainer(int ID,int weight){
        super(ID,weight);
    }
    public double consumption(){
        return 5.00*getWeight();
    }
}
