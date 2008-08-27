package {
	import flash.display.Stage;
	import flash.display.MovieClip;
	import flash.display.SimpleButton;
	import flash.events.MouseEvent;
	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import fl.motion.Color;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFormat;
	import flash.geom.Rectangle;
	import WikiHome;

	public class WikiButton extends MovieClip {
		private var wikiHome:WikiHome;
		private var thumbnail:String;
		private var navigationUrl:String;
		private var buttonLabelString:String;
		private var buttonLabel:TextField = new TextField();
		private var buttonFormat:TextFormat = new TextFormat();
		private var teaserText:String;
		private var imgLoader:Loader = new Loader();
		private var imgDisplay:MovieClip = new MovieClip();
		private var color:Color = new Color();

		public function WikiButton(pStage:WikiHome, pThumbnail:String, pUrl:String, pButtonLabelString:String, pTeaserText) {
			this.wikiHome = pStage;
			this.buttonMode = true;
			this.thumbnail = pThumbnail;
			this.navigationUrl = pUrl;
			this.buttonLabelString = pButtonLabelString;
			this.teaserText = pTeaserText;
			this.addThumbnail();
			this.addButtonLabel();

			this.addEventListener(MouseEvent.ROLL_OVER, playCircles);
			this.addEventListener(MouseEvent.ROLL_OUT, stopCircles);
			this.addEventListener(MouseEvent.CLICK, clickWikiButton);
			this.addEventListener(MouseEvent.MOUSE_DOWN, downWikiButton);
			this.addEventListener(MouseEvent.MOUSE_UP, upWikiButton);
		}
		private function playCircles(event:MouseEvent):void {
			this.play();
			this.imgDisplay.transform.colorTransform = this.color;
			this.wikiHome.setTeaserText(this.teaserText);
		}

		private function stopCircles(event:MouseEvent):void {
			
		}
		private function clickWikiButton(event:MouseEvent):void {
			var request:URLRequest = new URLRequest(this.navigationUrl);
			try {
				navigateToURL(request, "_self");
			} catch (e:Error) {
				trace("Error occurred!");
			}
		}
		
		private function downWikiButton(event:MouseEvent):void {
			this.imgDisplay.scaleX = 1.0;
			this.imgDisplay.scaleY = 0.95;
		}
		
		private function upWikiButton(event:MouseEvent):void {
			this.imgDisplay.scaleX = 1.0;
			this.imgDisplay.scaleY = 1.0;
		}
		
		private function addThumbnail():void {
			var image:URLRequest = new URLRequest(this.thumbnail);
			this.imgLoader.load(image);
			this.imgDisplay.addChild(imgLoader);
			addChild(imgDisplay);
			this.imgDisplay.x = -24;
			this.imgDisplay.y = -24;
		}
		
		private function addButtonLabel():void {
			this.buttonLabelTxt.text = this.buttonLabelString;
		}
	}
}