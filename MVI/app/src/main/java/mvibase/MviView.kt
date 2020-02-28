package mvibase

import io.reactivex.Observable


interface MviView<I: MviIntent, in S: MviViewState> {
    fun intent(): Observable<I>
    fun render(state: S)

}