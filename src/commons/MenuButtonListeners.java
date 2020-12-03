package commons;

import animal.AnimalTableViewBuilder;
import company.CompanyTableViewBuilder;
import shepherd.ShepherdTableViewBuilder;
import visualization.VisualizationViewBuilder;

/**
 * Itt csak tarolom a kulonbozo celokhoz tartozo actionListenerekt, hogy konnyen hozzaferhetoek mindenhonnan, ahol kell.
 */
public class MenuButtonListeners {
   public static MenuButtonListener mainMenuTarget = new MenuButtonListener(new MainMenuBuilder());
   public static MenuButtonListener animalListTarget = new MenuButtonListener(new AnimalTableViewBuilder());
   public static MenuButtonListener shepherdListTarget = new MenuButtonListener(new ShepherdTableViewBuilder());
   public static MenuButtonListener companyListTarget = new MenuButtonListener(new CompanyTableViewBuilder());
   public static MenuButtonListener dataVisualizationTarget = new MenuButtonListener(new VisualizationViewBuilder());
}
