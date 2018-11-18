package net.lustenauer.vaadindemo.service;

import net.lustenauer.vaadindemo.model.Plc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class BackgroundService {
    private final Logger logger = LoggerFactory.getLogger(BackgroundService.class);
    private final PlcService plcService;

    @Autowired
    public BackgroundService(PlcService plcService) {
        logger.trace("BackgroundService() called");

        Thread backgroundThread = new Thread(new MessagePrinterTask("Test"), "WORKER");
        backgroundThread.start();


        this.plcService = plcService;
    }

    @PreDestroy
    public void preDestroy() {
        logger.trace("preDestroy() called");

    }


    private class MessagePrinterTask implements Runnable {

        private String message;

        public MessagePrinterTask(String message) {
            logger.trace("MessagePrinterTask() called with: message = [" + message + "]");
            this.message = message;
        }

        public void run() {
            while (true) {
                try {
                    logger.debug("running...");
                    logger.info("plc services:= " + plcService.getPlcCount());

                    List<Plc> plcList = plcService.getAll();

                    for (Plc plc : plcList) {

                        if (!plc.isRunning()) {
                            plc.setRunning(true);
                            plcService.update(plc);
                            new Thread(new PlcReader(plc, plcService), "PLC-" + plc.getName().toUpperCase()
                                    .substring(0, Math.min(plc.getName().length(), 5))).start();
                        }

                    }


                    sleep(500);
                } catch (InterruptedException e) {
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
    }
}
