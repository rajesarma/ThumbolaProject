package in.sarma.thumbola.controller;

import in.sarma.thumbola.service.ThumbolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	ThumbolaService thumbolaService;
	SseEmitter emitter = new SseEmitter();
	ExecutorService executorService = Executors.newSingleThreadExecutor();
	boolean flag = true;

	@GetMapping("/newgame")
	public String newGame() {
		flag = false;

		if(!executorService.isShutdown()) {
			executorService.shutdown();
		}

		thumbolaService.clearBoard();

		/*SseEventBuilder event = SseEmitter.event().data(thumbolaService.recreatetable(0).toString());

		emitter = new SseEmitter();
		try {
			emitter.send(event);
		}catch (IOException e) {

		}*/

		return "gamepage";
	}

	@GetMapping("/start")
	@Async
	public SseEmitter startGame() {

		executorService = Executors.newSingleThreadExecutor();
		emitter = new SseEmitter();

		executorService.execute(() -> {
			try {
				for (int i = 0; flag; i++) {

					SseEventBuilder event = SseEmitter.event().data(thumbolaService.generatenum(true).toString());
					//							.data("SSE MVC - " + LocalTime.now().toString())
					//							.id(String.valueOf(i)).name("sse event - mvc");

					emitter.send(event);

					Thread.sleep(3000);
				}
			} catch (Exception ex) {
				emitter.completeWithError(ex);
			}
		});
		return emitter;
	}

	@GetMapping("/stop")
	public String stop() {
//		executorService.shutdown();
		flag = false;
		thumbolaService.clearBoard();
		return "gamepage";
	}



	@GetMapping("/pause")
	public SseEmitter pause() {
		flag = false;
		return startGame();
	}

	@GetMapping("/resume")
	public SseEmitter resume() {
		flag = true;
		return startGame();
	}

}
