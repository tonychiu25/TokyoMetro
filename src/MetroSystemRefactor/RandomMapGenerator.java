/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MetroSystemRefactor;
import java.util.Random;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
/**
 *
 * @author chiu.sintung
 */
public class RandomMapGenerator {
    
    public RandomMapGenerator() {
    }
    
    public int[][] generateRandomMap(int numNodes) {
        int[][] map = new int[numNodes][numNodes];
        Random randomGen = new Random();
        Integer randDistance, randTime, randCost,randPutPath;
        for (int i=0 ;i<numNodes; i++) {
            for(int j=0; j<=i; j++) {
                if (i == j) {
                    map[i][j] = 0;
                } else {
                    randPutPath = randomGen.nextInt(100);
                    if (randPutPath >= 20) {
                        randDistance = randomGen.nextInt(100);
                        randTime = randomGen.nextInt(100);
                        randCost = randomGen.nextInt(100);
                        map[i][j] = randDistance;
                    }
                }
            }
        }
        
        for(int i=0; i<numNodes; i++) {
            for (int j=numNodes-1; j>i; j--) {
                map[i][j] = map[j][i];
            }
        }
        
        return map;
    }
    
    public static void main(String args[]) {
        RandomMapGenerator randMap = new RandomMapGenerator();
        Random rand = new Random();
        int mapSize = 200;
        int map[][] = randMap.generateRandomMap(mapSize);
        for (int i=0; i<mapSize; i++) {
            for (int j=0; j<mapSize; j++) {
                System.out.print(map[i][j]+"-");
            }
            System.out.println("");
        }
        
        
        Writer write = new StringWriter();
        CSVWriter w = new CSVWriter(write);
        
        
    }
}
