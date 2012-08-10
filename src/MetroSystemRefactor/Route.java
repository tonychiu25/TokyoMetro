package MetroSystemRefactor;


public class Route {
    
    LineStationMediator lsMediator;
    Integer startIndex, endIndex, totalDistance, totalTime;     // Start/End station index
    
    public Route(LineStationMediator lsMediate, Integer startIndex, Integer endIndex) {
        lsMediator = lsMediate;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public void addStation(Integer sIndex) {
        
    }
    
}
