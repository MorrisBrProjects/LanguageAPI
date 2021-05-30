# LanguageAPI

Wie man sie Verwendet:


Main:
<pre><code>

		LanguagePlugin languageApi = getPlugin(LanguagePlugin.class);
		
		Registry registry = languageApi.getRegistry();
		LanguageRegistry languageRegistry = registry.getLanguageRegistry();
		
		languageRegistry.register("DE_DE", this);
		languageRegistry.register("EN_EN", this);
		languageRegistry.register("EU_EU", this);
		languageRegistry.register("ER_ER", this);
		languageRegistry.register("FR_FR", this);
		languageRegistry.register("RU_RU", this);
		languageRegistry.setDefaultLanguage("DE_DE");
		
		languageRegistry.unRegister("DEMO_DEMO");
		
		getServer().getPluginManager().registerEvents(new TestJoinListener(languageApi), this);

</code></pre>

Listener:
<pre><code>

public class TestJoinListener implements Listener {
	
	private LanguagePlugin languagePlugin;
	
	public TestJoinListener(LanguagePlugin languagePlugin) {
		this.languagePlugin = languagePlugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		Registry registry = languagePlugin.getRegistry();
		
		User user = registry.getUserRegistry().getUser(player.getUniqueId().toString());
		
		player.sendMessage(registry.getLanguageRegistry().getLanguagesAsName().toString());
		user.getProfile().changeLanguage("EN_EN");
		player.sendMessage(user.getProfile().getLanguage().getName());
		
	}

}

</code></pre>
