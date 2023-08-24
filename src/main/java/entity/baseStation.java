package entity;

public class baseStation {

        
        private long B;
        
        private long Hn;
        
        private long Pn;
        
        private long Pmec;
       
        private long N;
        public long getB(){
            return this.B;
        }
        public void setB(long b){
            this.B = b;
        }

        public long getHn(){
            return this.Hn;
        }
        public void setHn(long hn){
            this.Hn = hn;
        }

        public void setPn(long pn){
            this.Pn = pn;
        }

        public long getPn(){
            return this.Pn;
        }
        public void setN(long n){
            this.N = n;
        }

        public long getN(){
            return this.N;
        }
        public void setPmec(long pmec){
            this.Pmec = pmec;
        }

        public long getPmec(){
            return this.Pmec;
        }

       
        private String Name;
        public String getName(){
        return this.Name;
    }
        public void setName(String name){
        this.Name = name;
    }


}
