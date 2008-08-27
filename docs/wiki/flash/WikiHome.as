package {
	import flash.display.MovieClip;
	import flash.display.SimpleButton;
	import flash.events.MouseEvent;
	import flash.events.Event;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFormat;
	import flash.text.StyleSheet;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import WikiButton;

	public class WikiHome extends MovieClip {
		
		//Variables
		private var wikiXML:XML = new XML();
		private var xmRequest:URLRequest = new URLRequest('https://gforge.nci.nih.gov/svnroot/automation/trunk/docs/wiki/flash/resources/bda-wiki-home.xml');
		private var xmlLoader:URLLoader = new URLLoader(xmRequest);
		private var cssRequest:URLRequest = new URLRequest("https://gforge.nci.nih.gov/svnroot/automation/trunk/docs/wiki/flash/resources/bda-wiki-home.css");
		private var cssLoader:URLLoader = new URLLoader(cssRequest);
		private var defaultTeaserText:String;
		private var styles:StyleSheet = new StyleSheet();

		//Functions
		public function WikiHome() {
			this.xmlLoader.addEventListener(Event.COMPLETE, xmlLoadComplete);
			this.teaserTxt.multiline = true;
			this.teaserTxt.wordWrap = true;
		}

		private function xmlLoadComplete(evt:Event):void {
			this.wikiXML = XML(this.xmlLoader.data);
			this.parseWikiXML(this.wikiXML);
			this.cssLoader.addEventListener(Event.COMPLETE, cssLoadComplete);
		}
		
		private function parseWikiXML(pWikiXML:XML):void {
			this.defaultTeaserText = pWikiXML.welcome.toString();
			var buttonX:Number = 100;
			var buttonY:Number = 60;
			
			for (var buttonName:String in pWikiXML.button) {
				var aButton:WikiButton = new WikiButton(this, pWikiXML.button.image[buttonName], pWikiXML.button.link[buttonName], pWikiXML.button.name[buttonName], pWikiXML.button.teaser[buttonName]);
				aButton.x = buttonX;
				aButton.y = buttonY;
				addChild(aButton);
				buttonX += 140;
				if (buttonX > 400) {
					buttonX = 100;
					buttonY += 110;
				}
			}
		}
		
		private function cssLoadComplete(evt:Event):void {
			this.styles.parseCSS(this.cssLoader.data);
			this.teaserTxt.styleSheet = this.styles;
			var css:StyleSheet = new StyleSheet();
			css.parseCSS(URLLoader(evt.target).data);
			this.teaserTxt.styleSheet = css;
			this.teaserTxt.htmlText = this.defaultTeaserText;
		}

		public function setTeaserText(pTeaserText:String):void {
			this.teaserTxt.htmlText = pTeaserText;
		}
	}
}