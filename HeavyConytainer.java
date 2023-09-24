public class HeavyConytainer extends Container{
    public HeavyConytainer(int ID,int weight){
        super(ID ,weight);
    }
    public double consumption(){
        return 3.0 * getWeight();
    }
}
