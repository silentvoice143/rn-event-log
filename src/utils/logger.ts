class Logger {
  private enabled = true;

  setEnabled(enabled: boolean) {
    this.enabled = enabled;
  }

  log(...args: any[]) {
    if (!this.enabled) return;

    console.log('[RnEventLog]', ...args);
  }

  warn(...args: any[]) {
    if (!this.enabled) return;

    console.warn('[RnEventLog]', ...args);
  }

  error(...args: any[]) {
    if (!this.enabled) return;

    console.error('[RnEventLog]', ...args);
  }
}

export default new Logger();
