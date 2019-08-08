package in.sarma.thumbola.controller;

import in.sarma.thumbola.service.ThumbolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
//@Scope(WebApplicationContext.SCOPE_REQUEST)
//@RequestScope
public class HomeController {

	@Autowired
	ThumbolaService thumbolaService;

	@GetMapping("/")
	public String getHomePage() {


//		thumbolaService.prepareBoard();
//		thumbolaService.timer();

//		System.out.println(thumbolaService.getTable().toString());

		return "thumbolaPage";
	}

	/*@GetMapping(value = "/game", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //
	public ModelAndView getData(HttpServletRequest request) {

		System.out.println("sessionId : "+request.getRequestedSessionId());

		ModelAndView mav = new ModelAndView("homePage");
		mav.addObject("data", thumbolaService.getTable().toString());

		return mav;
	}*/



	@RequestMapping(path = "/api/events", method = RequestMethod.GET)
	public SseEmitter getEvents() throws Exception {
		SseEmitter emitter = new SseEmitter();
		ScheduledExecutorService pingThread = Executors.newScheduledThreadPool(1);
		emitter.onCompletion(() -> {
//			log.info("Complete");
			pingThread.shutdown();
		});
		emitter.onTimeout(() -> {
//			log.info("Timeout");
		});
		pingThread.scheduleAtFixedRate(() -> {
			try {
				emitter.send(SseEmitter.event().data("Hello").name("ping"));
			} catch (Exception e) {
//				log.error("Unable to emit", e);
				throw new RuntimeException(e);
			}
		} , 5000, 3000, TimeUnit.MILLISECONDS);
		return emitter;
	}



	private ExecutorService executor = Executors.newCachedThreadPool();

	@GetMapping("/rbe")
	public ResponseEntity<ResponseBodyEmitter> handleRbe() {
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();
//		executor.execute(() -> {
			try {
				emitter.send("/rbe" + " @ " + new Date(), MediaType.TEXT_EVENT_STREAM);
				emitter.complete();
			} catch (Exception ex) {
				emitter.completeWithError(ex);
			}
//		});
		return new ResponseEntity(emitter, HttpStatus.OK);
	}


	/*private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

	@GetMapping("/sse")
	public SseEmitter handleSse() {
		SseEmitter emitter = new SseEmitter();
		nonBlockingService.execute(() -> {
			try {
				emitter.send("/sse" + " @ " + new Date());
				// we could send more events
				emitter.complete();
			} catch (Exception ex) {
				emitter.completeWithError(ex);
			}
		});
		return emitter;
	}*/

	/*@GetMapping("/srb")
	public ResponseEntity<StreamingResponseBody> handleRbe() {
		StreamingResponseBody stream = out -> {
			String msg = "/srb" + " @ " + new Date();
			out.write(msg.getBytes());
		};
		return new ResponseEntity(stream, HttpStatus.OK);
	}*/


}
