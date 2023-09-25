public  class BasicContainer extends Container {
    //BsicContainer myobj= new BsicContainer(23320,3000);
    public BasicContainer(int ID,int weight,int portId){
        super(ID,weight, portId);
    }


    double consumption(){
        return 2.50 * getWeight();
    }
}
