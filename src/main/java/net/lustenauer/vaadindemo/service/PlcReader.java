package net.lustenauer.vaadindemo.service;

import net.lustenauer.vaadindemo.model.Plc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class PlcReader implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(PlcReader.class);

    private Plc plc;
    private final PlcService plcService;

    public PlcReader(Plc plc, PlcService plcService) {
        logger.trace("PlcReader() called with: plc = [" + plc + "]");
        this.plc = plc;
        this.plcService = plcService;

    }

    @Override
    public void run() {
        logger.trace("run() called");

        while (plc.isRunning()) {
            try {
                plc = plcService.get(plc.getId());
                if (plc == null){
                    break;  // break here thread when object is gone
                }
                logger.debug("{} is running ...", plc.getName());
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
