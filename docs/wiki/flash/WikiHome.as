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
		private var xmlUrl:URLRequest = new URLRequest('resources/bda-wiki-home.xml');
		private var xmlLoader:URLLoader = new URLLoader(xmlUrl);
		private var defaultTeaserText:String;
		
		private var aboutUrl:String = "https://wiki.nci.nih.gov/x/agGU";
		private var newsUrl:String = "https://wiki.nci.nih.gov/pages/viewrecentblogposts.action?key=BuildandDeploymentAutomation";
		private var docoUrl:String = "https://wiki.nci.nih.gov/x/MJx8";

		private var styles:StyleSheet = new StyleSheet();
		private var cssLoader:URLLoader = new URLLoader();

		public function WikiHome() {

			this.processWikiXML();

			buttonNews.addEventListener(MouseEvent.MOUSE_OVER, newsOver);
			buttonDoco.addEventListener(MouseEvent.MOUSE_OVER, docoOver);

			var aboutButtonDynamic:WikiButton = new WikiButton(this, 'images/amor.png', "https://wiki.nci.nih.gov/x/mQ6Z", 'About', 'about text');
			var newsButtonDynamic:WikiButton = new WikiButton(this, 'images/internet.png', "https://wiki.nci.nih.gov/pages/viewrecentblogposts.action?key=BuildandDeploymentAutomation", 'News', 'news text');

			aboutButtonDynamic.x = 60;
			aboutButtonDynamic.y = 60;

			newsButtonDynamic.x = 180;
			newsButtonDynamic.y = 60;

			//Set teaser text
			var cssRequest:URLRequest = new URLRequest("resources/bda-wiki-home.css");
			this.cssLoader.load(cssRequest);
			this.cssLoader.addEventListener(Event.COMPLETE, cssLoadComplete);

			this.teaserTxt.multiline = true;
			this.teaserTxt.wordWrap = true;
		}
		private function processWikiXML():void {
			this.xmlLoader.addEventListener(Event.COMPLETE, xmlLoadComplete);
		}

		private function xmlLoadComplete(evt:Event):void {
			this.wikiXML = XML(this.xmlLoader.data);
			this.parseWikiXML(this.wikiXML);
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
		// About button over
		private function aboutOver(event:MouseEvent):void {
			movTextbox.gotoAndPlay(2,null);
			movTextbox.stop();
		}

		// News button over
		private function newsOver(event:MouseEvent):void {
			movTextbox.gotoAndPlay(3,null);
			movTextbox.stop();
		}

		//Doco button over
		private function docoOver(event:MouseEvent):void {
			movTextbox.gotoAndPlay(4,null);
			movTextbox.stop();
		}

		public function setTeaserText(pTeaserText:String):void {
			this.teaserTxt.htmlText = pTeaserText;
		}
	}
}