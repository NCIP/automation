package gov.nih.nci.bda.wiki.events
{
	import flash.events.Event;

	public class WikiTeaserEvent extends Event
	{
		public var welcomeTeaser:String;
	
		public function WikiTeaserEvent(pWelcomeTeaser:String, type:String){
			super(type);
			this.welcomeTeaser = pWelcomeTeaser;
		}
	
		public override function clone():Event {
			return new WikiTeaserEvent(welcomeTeaser, type);
		}
	}
}