public class HeavyContainer extends Container{
    public HeavyContainer(int ID,int weight, int portId){
        super(ID ,weight, portId);
    }
    public double consumption(){
        return 3.0 * getWeight();
    }
}
