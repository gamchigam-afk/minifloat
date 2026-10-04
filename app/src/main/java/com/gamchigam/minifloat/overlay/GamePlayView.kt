package com.gamchigam.minifloat.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gamchigam.minifloat.game.GameResult
import com.gamchigam.minifloat.game.MiniGame
import com.gamchigam.minifloat.game.games.MemoryGame
import com.gamchigam.minifloat.game.games.NumberOrderGame
import com.gamchigam.minifloat.game.games.RandomButtonGame
import com.gamchigam.minifloat.game.games.ReactionGame
import com.gamchigam.minifloat.game.games.TapRushGame
import com.gamchigam.minifloat.game.games.TargetGame
import com.gamchigam.minifloat.game.games.TimingGame
import com.gamchigam.minifloat.game.games.DodgeGame
import java.util.Timer
import java.util.TimerTask

class GamePlayView(
    context: Context,
    private val game: MiniGame,
    private val onFinish: () -> Unit,
    private val onBack: () -> Unit
) : LinearLayout(context) {

    private val content = LinearLayout(context)
    private val title = TextView(context)
    private var timer: Timer? = null

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(20.dp(), 20.dp(), 20.dp(), 20.dp())

        background = GradientDrawable().apply {
            cornerRadius = 28.dp().toFloat()
            setColor(Color.rgb(21, 27, 35))
        }

        title.text = game.name
        title.textSize = 24f
        title.setTextColor(Color.WHITE)
        title.gravity = Gravity.CENTER

        addView(
            title,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                55.dp()
            )
        )

        addView(
            content,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                380.dp()
            )
        )

        val backButton = Button(context).apply {
            text = "게임 선택으로"
            setOnClickListener {
                stopTimer()
                onBack()
            }
        }

        addView(
            backButton,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                48.dp()
            )
        )
    }

    fun startGame() {
        content.removeAllViews()

        when (game) {
            is ReactionGame -> setupReaction(game)
            is TapRushGame -> setupTapRush(game)
            is TargetGame -> setupTarget(game)
            is MemoryGame -> setupMemory(game)
            is TimingGame -> setupTiming(game)
            is DodgeGame -> setupDodge(game)
            is NumberOrderGame -> setupNumberOrder(game)
            is RandomButtonGame -> setupRandomButton(game)
            else -> setupUnsupported()
        }
    }

    private fun setupReaction(game: ReactionGame) {
        val info = makeText("준비하세요...\n잠시 후 버튼이 나타납니다.")
        content.addView(info, matchParams(100))

        val button = Button(context).apply {
            text = "대기 중..."
            isEnabled = false
        }

        content.addView(button, matchParams(60))

        game.reset()

        timer = Timer()

        timer?.schedule(object : TimerTask() {
            override fun run() {
                post {
                    button.text = "지금!"
                    button.isEnabled = true
                    game.start()
                }
            }
        }, 1500)
        
        button.setOnClickListener {
            game.react()
            finishGame(game.finish())
        }
    }

    private fun setupTapRush(game: TapRushGame) {
        val counter = makeText("0 TAP")
        content.addView(counter, matchParams(80))

        val button = Button(context).apply {
            text = "TAP!"
            textSize = 24f
        }

        content.addView(button, matchParams(100))

        game.start()

        button.setOnClickListener {
            game.tap()
            counter.text = "${game.getTaps()} TAP"
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                post {
                    finishGame(game.finish())
                }
            }
        }, TapRushGame.GAME_TIME_MS)
    }

    private fun setupTarget(game: TargetGame) {
        val info = makeText("버튼을 최대한 정확하게 눌러보세요.")
        content.addView(info, matchParams(80))

        val button = Button(context).apply {
            text = "🎯 TARGET"
        }

        content.addView(button, matchParams(100))

        val status = makeText("성공: 0")
        content.addView(status, matchParams(50))

        game.start()

        button.setOnClickListener {
            val success = game.hit(
                0f,
                0f,
                0f,
                0f
            )

            if (success) {
                status.text = "성공: ${game.getHits()}"
            }
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                post {
                    finishGame(game.finish())
                }
            }
        }, 10_000)
    }

    private fun setupMemory(game: MemoryGame) {
        val info = makeText("숫자의 순서를 기억하세요.")
        content.addView(info, matchParams(60))

        game.start()

        val sequence = game.getSequence()

        val sequenceText = makeText(
            sequence.joinToString("  ")
        )

        content.addView(sequenceText, matchParams(70))

        val hideButton = Button(context).apply {
            text = "기억했어요"
        }

        content.addView(hideButton, matchParams(55))

        val inputArea = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        content.addView(
            inputArea,
            matchParams(100)
        )

        hideButton.setOnClickListener {
            sequenceText.text = "?"
            hideButton.isEnabled = false

            for (number in 1..4) {
                val button = Button(context).apply {
                    text = number.toString()

                    setOnClickListener {
                        val correct = game.input(number)

                        if (!correct) {
                            finishGame(game.finish())
                        } else if (game.getProgress() >= sequence.size) {
                            finishGame(game.finish())
                        }
                    }
                }

                inputArea.addView(
                    button,
                    LinearLayout.LayoutParams(
                        55.dp(),
                        55.dp()
                    )
                )
            }
        }
    }

    private fun setupTiming(game: TimingGame) {
        val info = makeText("정확히 2초 뒤에 눌러보세요.")
        content.addView(info, matchParams(90))

        val button = Button(context).apply {
            text = "START"
        }

        content.addView(button, matchParams(80))

        game.start()

        button.setOnClickListener {
            game.press()
            finishGame(game.finish())
        }
    }

    private fun setupDodge(game: DodgeGame) {
        val status = makeText("장애물을 피하세요!")
        content.addView(status, matchParams(80))

        val button = Button(context).apply {
            text = "💨 피하기"
            textSize = 20f
        }

        content.addView(button, matchParams(100))

        game.start()

        button.setOnClickListener {
            game.onHit()
            finishGame(game.finish())
        }

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                post {
                    game.update()
                    status.text =
                        "생존 시간: ${game.getSurvivedTime() / 1000.0}s"
                }
            }
        }, 0, 100)
    }

    private fun setupNumberOrder(game: NumberOrderGame) {
        val info = makeText("1부터 순서대로 누르세요.")
        content.addView(info, matchParams(60))

        game.start()

        val buttons = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER
        }

        content.addView(
            buttons,
            matchParams(260)
        )

        game.getNumbers().forEach { number ->
            val button = Button(context).apply {
                text = number.toString()

                setOnClickListener {
                    val correct = game.press(number)

                    if (!correct || game.getCorrectCount() >= 9) {
                        finishGame(game.finish())
                    }
                }
            }

            buttons.addView(
                button,
                LinearLayout.LayoutParams(
                    70.dp(),
                    45.dp()
                )
            )
        }
    }

    private fun setupRandomButton(game: RandomButtonGame) {
        val info = makeText("정답 버튼을 찾아 누르세요.")
        content.addView(info, matchParams(70))

        val buttons = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER
        }

        content.addView(
            buttons,
            matchParams(250)
        )

        game.start()

        repeat(RandomButtonGame.BUTTON_COUNT) { index ->
            val button = Button(context).apply {
                text = "버튼 ${index + 1}"

                setOnClickListener {
                    val finishedBefore =
                        game.getAttempts() >= RandomButtonGame.MAX_ROUNDS

                    game.press(index)

                    if (
                        finishedBefore ||
                        game.getAttempts() >= RandomButtonGame.MAX_ROUNDS
                    ) {
                        finishGame(game.finish())
                    } else {
                        info.text =
                            "정답을 찾아 누르세요.\n시도 ${game.getAttempts()}/${RandomButtonGame.MAX_ROUNDS}"
                    }
                }
            }

            buttons.addView(
                button,
                LinearLayout.LayoutParams(
                    200.dp(),
                    45.dp()
                )
            )
        }
    }

    private fun setupUnsupported() {
        content.addView(
            makeText("아직 준비 중인 게임입니다."),
            matchParams(100)
        )
    }

    private fun makeText(text: String): TextView {
        return TextView(context).apply {
            this.text = text
            textSize = 17f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
    }

    private fun finishGame(result: GameResult) {
        stopTimer()

        val resultView = ResultView(
            context = context,
            result = result,
            onRetry = {
                startGame()
            },
            onClose = {
                onFinish()
            }
        )

        removeAllViews()
        addView(
            resultView,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun matchParams(height: Int): LayoutParams {
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            height.dp()
        ).apply {
            setMargins(0, 5.dp(), 0, 5.dp())
        }
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
