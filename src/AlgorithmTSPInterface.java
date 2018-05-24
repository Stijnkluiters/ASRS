/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Stijn Kluiters
 */
public interface AlgorithmTSPInterface<T> {
  
  public Route getRoute();
  public <T> ArrayList<T> execute(ArrayList<T> products);
  
}
