public class RefrigeratedContainer extends HeavyContainer{
    public RefrigeratedContainer(int ID,int weight, int portId){
        super(ID,weight,portId);
    }
    public double consumption(){
        return 4.00*getWeight();
    }
}
