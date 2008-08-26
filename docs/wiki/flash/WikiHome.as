package {
	import flash.display.MovieClip;
	import flash.display.SimpleButton;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import WikiButton;

	public class WikiHome extends MovieClip {
		//Variables
		private var aboutUrl:String = "https://wiki.nci.nih.gov/x/agGU";
		private var newsUrl:String = "https://wiki.nci.nih.gov/pages/viewrecentblogposts.action?key=BuildandDeploymentAutomation";
		private var docoUrl:String = "https://wiki.nci.nih.gov/x/MJx8";
		public function WikiHome() {

			buttonAbout.addEventListener(MouseEvent.MOUSE_OVER, aboutOver);
			buttonAbout.addEventListener(MouseEvent.CLICK, aboutClick);

			buttonNews.addEventListener(MouseEvent.MOUSE_OVER, newsOver);
			buttonNews.addEventListener(MouseEvent.CLICK, newsClick);

			buttonDoco.addEventListener(MouseEvent.MOUSE_OVER, docoOver);
			buttonDoco.addEventListener(MouseEvent.CLICK, docoClick);
			
			var buttonDynamic:WikiButton = new WikiButton('images/amor.png', aboutUrl);

			addChild(buttonDynamic);
			buttonDynamic.x = 300;
			buttonDynamic.y = 300;
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

		// About button click
		private function aboutClick(event:MouseEvent):void {
			var request:URLRequest = new URLRequest(aboutUrl);
			try {
				//navigateToURL(request, '_self');
			} catch (e:Error) {
				trace("Error occurred!");
			}
		}

		// About button click
		private function newsClick(event:MouseEvent):void {
			var request:URLRequest = new URLRequest(newsUrl);
			try {
				//navigateToURL(request, '_self');
			} catch (e:Error) {
				trace("Error occurred!");
			}
		}

		// Doco button click
		private function docoClick(event:MouseEvent):void {
			var request:URLRequest = new URLRequest(docoUrl);
			try {
				//navigateToURL(request, '_self');
			} catch (e:Error) {
				trace("Error occurred!");
			}
		}
	}
}