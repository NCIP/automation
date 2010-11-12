package gov.nih.nci.bda.wiki.vo
{
	[Bindable]
	public class WikiButtonVO
	{
		
		public var buttonID:Number;
		public var buttonText:String;
		public var buttonImage:String;
		public var buttonLink:String;
		public var teaser:String;
	
		public function WikiButtonVO (pButtonID:Number, pButtonText:String, pButtonImage:String, pButtonLink:String, pTeaser:String)
		{
			buttonID = pButtonID;
			buttonText = pButtonText;
			buttonImage = pButtonImage;
			buttonLink = pButtonLink;
			teaser = pTeaser;
		}

		public function toString():String
		{
			return "[Button]" + this.buttonText;
		}
		
		public static function buildWikiButton(o:Object):WikiButtonVO
		{
			return new WikiButtonVO(o.buttonID, o.buttonText, o.buttonImage, o.buttonLink, o.teaser);
		}
	}
}