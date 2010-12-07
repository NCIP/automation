package gov.nih.nci.bda.wiki.events
{
	import flash.events.Event;

	public class WikiButtonEvent extends Event
	{
		public var buttonArray:Array;
	
		public function WikiButtonEvent(pButtonArray:Array, type:String){
			super(type);
			this.buttonArray = pButtonArray;
		}
	
		public override function clone():Event {
			return new WikiButtonEvent(buttonArray, type);
		}
	}
}