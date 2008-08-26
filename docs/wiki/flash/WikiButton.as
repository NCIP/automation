package {
	import flash.display.MovieClip;
	import flash.display.SimpleButton;
	import flash.events.MouseEvent;
	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import fl.motion.Color;

	public class WikiButton extends MovieClip {

		private var thumbnail:String;
		private var navigationUrl:String;
		private var imgLoader:Loader = new Loader();
		private var imgDisplay:MovieClip = new MovieClip();
		private var color:Color = new Color();

		public function WikiButton(pThumbnail:String, pUrl:String) {
			this.buttonMode = true;
			this.thumbnail = pThumbnail;
			this.navigationUrl = pUrl;
			this.addThumbnail();
			
			this.addEventListener(MouseEvent.ROLL_OVER, playCircles);
			this.addEventListener(MouseEvent.ROLL_OUT, stopCircles);
			this.addEventListener(MouseEvent.CLICK, clickWikiButton);
		}
		
		private function playCircles(event:MouseEvent):void {
			this.play();
			this.imgDisplay.transform.colorTransform = this.color;
		}

		private function stopCircles(event:MouseEvent):void {
			
		}
		
		private function clickWikiButton(event:MouseEvent):void {
			var request:URLRequest = new URLRequest(this.navigationUrl);
			try {
				navigateToURL(request, '_self');
			} catch (e:Error) {
				trace("Error occurred!");
			}
		}
		
		private function addThumbnail():void {
			var image:URLRequest = new URLRequest(this.thumbnail);
			this.imgLoader.load(image);
			this.imgDisplay.addChild(imgLoader);
			addChild(imgDisplay);
			imgDisplay.x = -24;
			imgDisplay.y = -24;
		}
		
	}
}