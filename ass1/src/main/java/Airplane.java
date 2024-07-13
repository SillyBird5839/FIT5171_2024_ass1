    public class Airplane
    {
        private int airplaneID;
        private String airplaneModel;
        private int businessSitsNumber;
        private int economySitsNumber;
        private int crewSitsNumber;
    
        public Airplane(int airplaneID, String airplaneModel, int businessSitsNumber, int economySitsNumber, int crewSitsNumber)
        {
            // ydai0035 use set functions to replace the original recording methods
            setAirplaneID(airplaneID);
            setAirplaneModel(airplaneModel);
            setBusinessSitsNumber(businessSitsNumber);
            setEconomySitsNumber(economySitsNumber);
            setCrewSitsNumber(crewSitsNumber);
        }

    public int getAirplaneID() {
        return airplaneID;
    }

    public void setAirplaneID(int airplaneID) {
        // ydai0035
        if (airplaneID > 0) {
            this.airplaneID = airplaneID;
        } else {
//            System.out.println("Error: Airplane ID must be positive.");
            throw new IllegalArgumentException("Airplane ID must be positive.");
        }
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        // ydai0035
        if (airplaneModel != null && !airplaneModel.isEmpty()) {
            this.airplaneModel = airplaneModel;
        } else {
//            System.out.println("Error: Airplane model cannot be null or empty.");
            throw new IllegalArgumentException("Airplane model cannot be null or empty.");
        }
    }

    public int getBusinessSitsNumber() {
        return businessSitsNumber;
    }

    public void setBusinessSitsNumber(int businessSitsNumber) {
        // ydai0035
        if (businessSitsNumber >= 11 && businessSitsNumber <= 30) {
            this.businessSitsNumber = businessSitsNumber;
        } else {
//            System.out.println("Error: Business sits number must be between 1 and 300.");
            throw new IllegalArgumentException("Business sits number must be between 11 and 30.");
        }
    }

    public int getEconomySitsNumber() {
        return economySitsNumber;
    }

        public void setEconomySitsNumber(int economySitsNumber) {
            // ydai0035
            if (economySitsNumber >= 31 && economySitsNumber <= 300) {
                this.economySitsNumber = economySitsNumber;
            } else {
//                System.out.println("Error: Economy sits number must be between 1 and 300.");
                throw new IllegalArgumentException("Economy sits number must be between 31 and 300.");
            }
        }

    public int getCrewSitsNumber() {
        return crewSitsNumber;
    }

    public void setCrewSitsNumber(int crewSitsNumber) {
        // ydai0035
        if (crewSitsNumber >= 1 && crewSitsNumber <= 10) {
            this.crewSitsNumber = crewSitsNumber;
        } else {
//            System.out.println("Error: Crew sits number must be between 1 and 300.");
            throw new IllegalArgumentException("Crew sits number must be between 1 and 10.");
        }
    }

    public String toString() {
        // ydai0035 change the form of output and the methods to get the information
            return "Airplane{" +
                    "model='" + getAirplaneModel() + "', " +
                    "business sits=" + getBusinessSitsNumber() + ", " +
                    "economy sits=" + getEconomySitsNumber() + ", " +
                    "crew sits=" + getCrewSitsNumber() +
                    '}';
        }


    public static Airplane getAirPlaneInfo(int airplane_id) {
        // TODO Auto-generated method stub
        return null;
    }
}