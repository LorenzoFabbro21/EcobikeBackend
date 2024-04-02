import { Subject, Subscription } from 'rxjs';
import { Observer } from 'rxjs/internal/types';


export interface BroadCastElementList {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [key:string]: Subject<any>
}

///
/// This class is used to broadcast messages between components.
/// It is a singleton class.
///  - To get the instance of the class, use MessageBroadcast.Instance
///  - To create a new message, use MessageBroadcast.Instance.getOrCreateMessage<T>(listenerKey)  where T is the type of the message
///  - To destroy a message, use MessageBroadcast.Instance.destroyMessage(listenerKey)
///  - To add a listener to a message, use MessageBroadcast.Instance.addListener<T>(listenerKey, onNextCallback, onErrorCallback, onCompleteCallback)
///  - To remove a listener from a message, use MessageBroadcast.Instance.removeListener(listenerKey)
///
export class MessageBroadcast {

  /// <summary>
  /// The instance of the MessageBroadcast class.
  /// </summary>
  /**
   * The instance of the MessageBroadcast class.
   */
  private static instance: MessageBroadcast;
  /**
   * The list of messages.
   */
  private static broadcastMessages: BroadCastElementList;

  /**
 * The constructor of the MessageBroadcast class.
 * @returns The instance of the MessageBroadcast class.
 *
 */
  private constructor () {
    MessageBroadcast.broadcastMessages = {};
    return;
  }

  /**
   * Get the instance of the MessageBroadcast class.
   * @returns The instance of the MessageBroadcast class.
   * @example
   * const messageBroadcast = MessageBroadcast.Instance;
   */
  public static get Instance (): MessageBroadcast {
    return MessageBroadcast.instance || (MessageBroadcast.instance = new this());
  }

  /**
   *  Get or create a message.
   * - If the message does not exist, it is created.
   * - If the message exists, it is returned.
   * @param listenerKey The key of the message.
   * @returns The message.
   */
  getOrCreateMessage<T> (listenerKey: string): Subject<T> {
    if (!MessageBroadcast.broadcastMessages[listenerKey] || MessageBroadcast.broadcastMessages[listenerKey].closed) {
      MessageBroadcast.broadcastMessages[listenerKey] = new Subject<T>();
    }

    return MessageBroadcast.broadcastMessages[listenerKey];
  }

  /**
   * Destroy a message.
   * - If the message does not exist, nothing is done.
   * - If the message exists, it is destroyed.
   * @param listenerKey The key of the message.
   * @returns Nothing.
   */
  destroyMessage (listenerKey: string): void {
    if (MessageBroadcast.broadcastMessages[listenerKey] && !MessageBroadcast.broadcastMessages[listenerKey].closed) {
      MessageBroadcast.broadcastMessages[listenerKey].unsubscribe();
    }
    delete MessageBroadcast.broadcastMessages[listenerKey];
  }

  /**
   *  Add a listener to a message.
   * - If the message does not exist, nothing is done.
   * - If the message exists, the listener is added.
   * @param listenerKey The key of the message.
   * @param onNextCallback The callback method called when a message is received.
   * @param onErrorCallback The callback method called when an error occurs.
   * @param onCompleteCallback The callback method called when the message is completed.
   * @returns The subscription to the message.
   */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  addListener<T> (listenerKey: string, onNextCallback: (value: T)=>void, onErrorCallback?: (errorData:any) => void, onCompleteCallback?: () => void ): Subscription |null {
    if (MessageBroadcast.broadcastMessages[listenerKey]) {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const errorCallbackMethod = (errorDefaultData: any):void => {
        console.error(errorDefaultData.toString() ?? ""); return;
      };
      const completeCallbackMethod = ():void => {
        return;
      };
      const observer: Observer<T> = {
        next: onNextCallback,
        error: onErrorCallback ?? errorCallbackMethod,
        complete: onCompleteCallback ?? completeCallbackMethod
      };
      const subject = MessageBroadcast.broadcastMessages[listenerKey];
      return subject.subscribe(observer);
    }
    else {
      return null;
    }
  }

  /**
   * Remove a listener from a message.
   * - If the message does not exist, nothing is done.
   * - If the message exists, the listener is removed.
   *
   * @param listenerKey The key of the message.
   * @returns Nothing.
   */
  removeListener (listenerKey: string): void {
    if (MessageBroadcast.broadcastMessages[listenerKey]) {
      const subject = MessageBroadcast.broadcastMessages[listenerKey];
      subject.unsubscribe();
    }
  }

}
