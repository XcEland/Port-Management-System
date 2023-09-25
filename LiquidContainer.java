public class LiquidContainer extends HeavyContainer{
    public LiquidContainer(int ID,int weight, int portId){
        super(ID,weight,portId);
    }

    public double consumption(){
        return 5.00*getWeight();
    }
}
