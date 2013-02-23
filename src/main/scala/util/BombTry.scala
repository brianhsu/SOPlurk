package org.bone.soplurk.util

import scala.util.Try
import scala.util.Success
import scala.util.Failure

/**
 *  == BombTry ==
 *
 *  This `implicit class BombTry` in this object is intend to enrich the
 *  built-in `scala.util.Try[T]`.
 *
 *  `Try[T]` with for-comprehension is a very useful constrcut to chain 
 *  multiple functions that may go wrong.
 *
 *  For example, if we have two method that wrapped thier result in `Try[T]`,
 *  we can use for-comprehension to make sure the body will only be executed
 *  when both method are successed.
 *
 *  {{{
 *
 *    import scala.util._
 *
 *    def operationA: Try[Int] = Success(1)
 *    def operationB: Try[Int] = Failure(new Exception("error"))
 *
 *    for {
 *      valueA <- operationA
 *      valueB <- operationB
 *    } {
 *      println("A:" + valueA)
 *      println("B:" + valueB)
 *    }
 *    
 *  }}}
 *
 *  But here is a problem in the above code, nothing will happens if `operationA`
 *  or `operationB` is a Failure. The exception simply be swallowed, no one will
 *  notice there is an exception, this is not a good thing.
 *
 *  Sometimes we hope our program crash as soon as possible if there is any problem
 *  that we are not expected, instead of just ignore that problem.
 *
 *  So here is our enriched `Try[T]`, it will throw the exception as soon as there
 *  are `Failure` in the for-comprehension.
 *
 *  {{{
 *
 *    import scala.util._
 *    import org.bone.soplurk.util.BombTry._
 *
 *    def operationA: Try[Int] = Success(1)
 *    def operationB: Try[Int] = Failure(new Exception("error"))
 *
 *    for {
 *      valueA <- operationA.boomIfFailed
 *      valueB <- operationB.boomIfFailed
 *    } {
 *      println("A:" + valueA)
 *      println("B:" + valueB)
 *    }
 *
 *  }}}
 *
 *  This syntax of this code is similar to the first one, but it will throw out 
 *  the exception once there are any Failure in the for-comprehension.
 *
 *
 */
object BombTry {

  implicit class BombTry[T](val result: Try[T]) {

    /**
     *  Get the Success projection or throw an exception
     */
    def boomIfFailed: Try[T] = result match {
      case Success(value)     => result
      case Failure(exception) => throw exception
    }

  }

}
