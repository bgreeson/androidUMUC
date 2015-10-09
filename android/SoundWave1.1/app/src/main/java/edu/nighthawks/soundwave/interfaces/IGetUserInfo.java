package edu.nighthawks.soundwave.interfaces;

/**
 * Created by joe.keefe on 10/7/2015.
 *
 * This interface defines the contract between user info used in actions and
 * the application config.
 *
 */
public interface IGetUserInfo
{
    public String getUserEmail();
    public String getUserDiplayName();
    public int getUserId();
}
