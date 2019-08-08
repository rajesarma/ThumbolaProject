package in.sarma.thumbola.controller;

import in.sarma.thumbola.service.ThumbolaService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
@RequestMapping("/thumbola")
public class ThumbolaController {

	@Autowired
	ThumbolaService thumbolaService;

	boolean flag = true;

	@GetMapping(path = "/start_game", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<String> start() {

		/*StringBuilder data;
		if(flag) {
			data = thumbolaService.generatenum();
		} else {
			data = thumbolaService.recreatetable(0);
		}*/

		if(flag) {
			return Flux.interval(Duration.ofSeconds(3))
					.onBackpressureDrop()
					.map(x -> thumbolaService.generatenum(flag).toString());
		} else {
//			Flux<String> data =
//					Flux.generate(sink -> sink.next(thumbolaService.recreatetable(0).toString()));

//			Mono.when(data).block();

			return Mono.just(thumbolaService.recreatetable(0).toString());

		}
	}

	@GetMapping(path = "/new_game")
	public ModelAndView newGame() {
		ModelAndView mav = new ModelAndView("thumbolaPage");
		mav.addObject("gameData", thumbolaService.recreatetable(0).toString());
		return mav;
	}

	@GetMapping(path = "/pause_game")
	public ModelAndView pauseGame() {
		ModelAndView mav = new ModelAndView("thumbolaPage");
		flag = false;
		mav.addObject("gameData", thumbolaService.generatenum(flag).toString());
		return mav;
	}

	@GetMapping(path = "/continue_game")
	public Publisher<String> continueGame() {
//		ModelAndView mav = new ModelAndView("thumbolaPage");
		flag = true;
//		mav.addObject("gameData", thumbolaService.generatenum(flag).toString());
		return Flux.interval(Duration.ofSeconds(3))
				.onBackpressureDrop()
				.map(x -> thumbolaService.generatenum(flag).toString());
	}
}

	/*@GetMapping(path = "/new_game", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> newGame() {
		return Flux.interval(Duration.ofSeconds(1))
				.onBackpressureDrop()
				.map(x -> thumbolaService.recreatetable(0).toString());
	}*/

	/*
	@GetMapping(path = "/pause_game")
	public Publisher<String> pauseGame() {
		flag = false;
		return Mono.just(thumbolaService.recreatetable(0).toString());
	}

	@GetMapping(path = "/continue_game")
	public Publisher<String> continueGame() {
		flag = true;
		return Mono.just(thumbolaService.recreatetable(0).toString());
	}*/

