package com.ullarah.ulottery;

import java.util.Map;
import java.util.UUID;

class LotteryTask extends LotteryFunction {

    LotteryTask() {
        deathLotteryStart();
    }

    private void deathLotteryStart() {

        LotteryInit.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(
                LotteryInit.getPlugin(), () -> {

                    if (!getPause().isPaused()) {

                        getDuration().setCount(getDuration().getCount() + 1);

                        if (getCountdown().getCount() > 0) getCountdown().setCount(getCountdown().getCount() - 1);
                        else lotteryFinished();

                    }

                    if (getSuspension().getMap().size() > 0) {

                        for (Map.Entry<UUID, Integer> entry : getSuspension().getMap().entrySet()) {

                            UUID uuid = entry.getKey();
                            Integer timeout = entry.getValue() - 1;

                            if (timeout <= 0) getSuspension().getMap().remove(uuid);
                            else getSuspension().getMap().put(uuid, timeout);

                        }

                    }

                }, 0, 1200);

    }

}
