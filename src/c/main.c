# include <jni.h>
# include "JKey.h"
# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <ctype.h>
# include <termios.h>

/* get a single char from stdin    */
int getch(void)
{
   struct termios oldattr, newattr;
   int ch;
   tcgetattr(0, &oldattr);
   newattr=oldattr;
   newattr.c_lflag &= ~( ICANON | ECHO );
   tcsetattr( 0, TCSANOW, &newattr);
   ch=getchar();
   tcsetattr(0, TCSANOW, &oldattr);
   return(ch);
}

JNIEXPORT jchar JNICALL Java_io_github_devlinuxuser_JKey_readKey(JNIEnv *env, jobject thisObj){
 return getch();
}
