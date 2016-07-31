package com.ullarah.ulottery;

import com.ullarah.ulottery.message.*;
import org.bukkit.configuration.file.FileConfiguration;

public class LotteryMessageInit {

    private static RecentWinner recentWinner;
    private static Bank bank;
    private static Block block;
    private static Countdown countdown;
    private static Duration duration;
    private static History history;
    private static Pause pause;
    private static RecentDeath recentDeath;
    private static Suspension suspension;
    private static Exclude exclude;

    static RecentWinner getRecentWinner() {
        return recentWinner;
    }

    private static void setRecentWinner(RecentWinner recentWinner) {
        LotteryMessageInit.recentWinner = recentWinner;
    }

    static Bank getBank() {
        return bank;
    }

    private static void setBank(Bank bank) {
        LotteryMessageInit.bank = bank;
    }

    static Block getBlock() {
        return block;
    }

    private static void setBlock(Block block) {
        LotteryMessageInit.block = block;
    }

    static Countdown getCountdown() {
        return countdown;
    }

    private static void setCountdown(Countdown countdown) {
        LotteryMessageInit.countdown = countdown;
    }

    static Duration getDuration() {
        return duration;
    }

    private static void setDuration(Duration duration) {
        LotteryMessageInit.duration = duration;
    }

    static History getHistory() {
        return history;
    }

    private static void setHistory(History history) {
        LotteryMessageInit.history = history;
    }

    static Pause getPause() {
        return pause;
    }

    private static void setPause(Pause pause) {
        LotteryMessageInit.pause = pause;
    }

    static RecentDeath getRecentDeath() {
        return recentDeath;
    }

    private static void setRecentDeath(RecentDeath recentDeath) {
        LotteryMessageInit.recentDeath = recentDeath;
    }

    static Suspension getSuspension() {
        return suspension;
    }

    private static void setSuspension(Suspension suspension) {
        LotteryMessageInit.suspension = suspension;
    }

    static Exclude getExclude() {
        return exclude;
    }

    private static void setExclude(Exclude exclude) {
        LotteryMessageInit.exclude = exclude;
    }

    static void setDefaults(FileConfiguration config) {

        setExclude(new Exclude());

        setSuspension(new Suspension());
        getSuspension().setTime(config.getInt("suspension"));

        setPause(new Pause());
        getPause().setTotal(config.getInt("players"));

        setCountdown(new Countdown());
        getCountdown().setCount(config.getInt("countdown"));
        getCountdown().setOriginal(config.getInt("countdown"));

        setDuration(new Duration());

        setBlock(new Block());
        getBlock().setLimit(config.getInt("blocks"));

        setRecentWinner(new RecentWinner());
        getRecentWinner().setVaultAmount(config.getInt("vault.amount"));
        getRecentWinner().setItemAmount(config.getInt("item.amount"));

        setBank(new Bank());
        getBank().setItemMaterial(config.getString("item.material"));

        setHistory(new History());
        setRecentDeath(new RecentDeath());

    }

}
