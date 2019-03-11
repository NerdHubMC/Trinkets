package nerdhub.trinkets.client.gui;

/**
 * @author BrockWS
 */
public interface ITabScreen {

    int getSelectedTab();

    void onSelectedChange(int selected);
}
