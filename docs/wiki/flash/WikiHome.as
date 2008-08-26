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


			buttonNews.addEventListener(MouseEvent.MOUSE_OVER, newsOver);
			buttonNews.addEventListener(MouseEvent.CLICK, newsClick);

			buttonDoco.addEventListener(MouseEvent.MOUSE_OVER, docoOver);
			buttonDoco.addEventListener(MouseEvent.CLICK, docoClick);
			
			var aboutButtonDynamic:WikiButton = new WikiButton('images/amor.png', "https://wiki.nci.nih.gov/x/mQ6Z", 'About');
			var newsButtonDynamic:WikiButton = new WikiButton('images/internet.png', "https://wiki.nci.nih.gov/pages/viewrecentblogposts.action?key=BuildandDeploymentAutomation", 'News');

			addChild(aboutButtonDynamic);
			addChild(newsButtonDynamic);
			
			aboutButtonDynamic.x = 60;
			aboutButtonDynamic.y = 60;
			
			newsButtonDynamic.x = 180;
			newsButtonDynamic.y = 60;
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