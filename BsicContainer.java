public  class BsicContainer extends Container {
    //BsicContainer myobj= new BsicContainer(23320,3000);
    public BsicContainer(int ID,int weight){
        super(ID,weight);
    }
    double consumption(){
        return 2.50 * getWeight();
    }
}
